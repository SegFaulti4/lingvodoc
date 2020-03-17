from sqlalchemy import func, and_

from lingvodoc.models import (
    Client,
    DBSession,
    Dictionary,
    DictionaryPerspective,
    LexicalEntry,
    TranslationGist,
    Entity
)


def gc_message(timestamp, reason, initiator):
    """
    This function generates a message for additional metadata to track reason of garbage collection.
    :param timestamp: time of gc-loop start
    :param reason: text reason for deletion
    :param initiator: object that initially became a reason for deletion (it can be some of parents objects of self)
    :return:
    """
    return {"garbage_collector":
                {"deleted_at": timestamp,
                 "reason": reason,
                 "initiator":
                     {"client_id": initiator.client_id,
                      "object_id": initiator.object_id,
                      "tablename": initiator.__tablename__}
                 }
            }


def get_empty_perspectives():
    """
    This function detects perspectives that don't have any lexical entries
    :return: list
    """
    # NOTE: don't listen to PyCharm; here we must use '==' comparison instead of 'is'
    perspectives = DBSession.query(DictionaryPerspective).outerjoin(LexicalEntry)\
        .filter(DictionaryPerspective.lexicalentry == None)
    return perspectives


def get_empty_dictionaries():
    """
    This function detects dictionaries that don't have any perspectives
    :return: list
    """
    dictionaries = DBSession.query(Dictionary).outerjoin(DictionaryPerspective)\
        .filter(Dictionary.dictionaryperspective == None)
    return dictionaries


def get_useless_dictionaries():
    """
    This function detects dictionaries that have all the perspectives empty
    :return: tuple
    """
    empty_perspectives = set(get_empty_perspectives())
    empty_dictionaries = get_empty_dictionaries()

    all_dictionaries = DBSession.query(Dictionary).outerjoin(DictionaryPerspective)\
        .filter(Dictionary.dictionaryperspective != None)
    useful_dictionaries = []
    no_content_dictionaries = []

    def is_useful(dictionary):
        for perspective in dictionary.dictionaryperspective:
            if perspective not in empty_perspectives:
                return True
        return False

    for dictionary in all_dictionaries:
        useful_dictionaries.append(dictionary) if is_useful(dictionary) else no_content_dictionaries.append(dictionary)



    return empty_dictionaries, no_content_dictionaries, useful_dictionaries


def get_null_entities():
    """
    This function detects entities that have null content and are not links or self links
    :return: list
    """
    # NOTE: don't listen to PyCharm; here we must use '==' comparison instead of 'is'
    return DBSession.query(Entity).filter(and_(Entity.content == None,
                                              Entity.self_client_id == None,
                                              Entity.link_client_id == None))


def get_empty_entities():
    """
    This function detects entities that have empty string content
    :return: list
    """
    return DBSession.query(Entity).filter(Entity.content == '')


def get_empty_lexical_entries():
    """
    This function detects lexical entries that don't have any entities
    :return: list
    """
    le = DBSession.query(LexicalEntry).filter(LexicalEntry == None)
    return le


def get_orphaned_translations():
    """
    TODO:
    This function detects translation gists that have only deleted parents
    :return: list
    """
    return
